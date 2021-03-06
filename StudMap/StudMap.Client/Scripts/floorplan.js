//
//   Copyright 2012 David Ciarletta
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//

d3.floorplan = function() {
    var layers = [],
        panZoomEnabled = true,
        maxZoom = 10,
        xScale = d3.scale.linear(),
        yScale = d3.scale.linear();
    var zoom = null;

    function map(g) {
        var width = xScale.range()[1] - xScale.range()[0],
            height = yScale.range()[1] - yScale.range()[0];

        g.each(function(data) {
            if (!data) return;

            var g = d3.select(this);

            initDefs(g.selectAll("defs").data([0]).enter().append("defs"));

            // setup container for layers and area to capture events
            var vis = g.selectAll(".map-layers").data([0]),
                visEnter = vis.enter().append("g").attr("class", "map-layers"),
                visUpdate = d3.transition(vis);

            visEnter.append("rect")
                .attr("class", "canvas")
                .attr("pointer-events", "all")
                .style("opacity", 0);

            visUpdate.attr("width", width)
                .attr("height", height)
                .attr("x", xScale.range()[0])
                .attr("y", yScale.range()[0]);

            //// render and reorder layers
            var maplayers = vis.selectAll(".maplayer")
                .data(layers, function(l) { return l.id(); });
            maplayers.enter()
                .append("g")
                .attr("class", function(l) { return "maplayer " + l.title(); })
                .append("g")
                .attr("class", function(l) { return l.id(); })
                .datum(null);
            maplayers.exit().remove();
            maplayers.order();

            // redraw layers
            maplayers.each(function (layer) {
                var wrapper = d3.select(this);
                var el = wrapper.select("g." + layer.id());
                el.datum(data[layer.id()]).call(layer);
            });


            zoom = d3.behavior.zoom().scaleExtent([1, maxZoom])
                .on("zoom", function() {
                    if (panZoomEnabled) {
                        setView(g, d3.event.scale, d3.event.translate);
                    }
                });
            // add pan - zoom behavior
            g.call(zoom);

        });
    }

    map.xScale = function(scale) {
        if (!arguments.length) return xScale;
        xScale = scale;
        layers.forEach(function(l) { l.xScale(xScale); });
        return map;
    };

    map.yScale = function(scale) {
        if (!arguments.length) return yScale;
        yScale = scale;
        layers.forEach(function(l) { l.yScale(yScale); });
        return map;
    };

    map.panZoom = function(enabled) {
        if (!arguments.length) return panZoomEnabled;
        panZoomEnabled = enabled;
        return map;
    };

    map.zoom = function(tx, ty, s) {
        zoom.scale(s);
        zoom.translate([tx, ty]);
        setView(d3.select('#svgMap'), zoom.scale(), zoom.translate());
    };

    map.addLayer = function(layer, index) {
        layer.xScale(xScale);
        layer.yScale(yScale);

        if (arguments.length > 1 && index >= 0) {
            layers.splice(index, 0, layer);
        } else {
            layers.push(layer);
        }

        return map;
    };

    map.callPathplotLayer = function(data) {

        for (var i = 0; i < layers.length; i++) {

            if (layers[i].id() === "fp-pathplot") {
                
                d3.select(".maplayer.pathplot").datum(data).call(layers[i]);
                break;
            }
        }
    };
    
    map.callGraphLayer = function (data) {

        for (var i = 0; i < layers.length; i++) {

            if (layers[i].id() === "fp-graph") {

                d3.select(".maplayer.graph").datum(data).call(layers[i]);
                break;
            }
        }
    };

    function setView(g, s, t) {
        if (!g) return;
        if (s) g.__scale__ = s;
        if (t && t.length > 1) g.__translate__ = t;

        // limit translate to edges of extents
        var minXTranslate = (1 - g.__scale__) *
            (xScale.range()[1] - xScale.range()[0]);
        var minYTranslate = (1 - g.__scale__) *
            (yScale.range()[1] - yScale.range()[0]);

        g.__translate__[0] = Math.min(xScale.range()[0],
            Math.max(g.__translate__[0], minXTranslate));
        g.__translate__[1] = Math.min(yScale.range()[0],
            Math.max(g.__translate__[1], minYTranslate));
        g.selectAll(".map-layers")
            .attr("transform",
                "translate(" + g.__translate__ +
                    ")scale(" + g.__scale__ + ")");
    }
    
    function initDefs(selection) {
        selection.each(function () {
            var defs = d3.select(this);

            var markerStart = defs.append("marker")
            .attr("id", "StartMarker")
            .attr("viewBox", "0 0 6 6")
            .attr("refX", "3")
            .attr("refY", "6")
            .attr("markerWidth", "1" )
            .attr("markerHeight", "1" );

            markerStart.append("image")
                .attr("x", "0")
                .attr("y","0")
                .attr("width", "6")
                .attr("height","6")
                .attr("xlink:href", "Content/Images/location.svg");

            var markerEnd = defs.append("marker")
            .attr("id", "EndMarker")
            .attr("viewBox", "0 0 6 6")
            .attr("refX", "3")
            .attr("refY", "6")
            .attr("markerWidth", "1")
            .attr("markerHeight", "1");

            markerEnd.append("image")
                .attr("x", "0")
                .attr("y", "0")
                .attr("width", "6")
                .attr("height", "6")
                .attr("xlink:href", "Content/Images/finish_flag.svg");
        });
    }

    return map;
};

d3.floorplan.version = "0.1.1";