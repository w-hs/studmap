﻿using System.Collections.Generic;
using System.Linq;
using QuickGraph;
using QuickGraph.Algorithms.ShortestPath;
using StudMap.Core.Graph;
using StudMap.Core.Maps;
using StudMap.Data.Entities;
using StudMap.Service.Services;

namespace StudMap.Service.CacheObjects
{
    public class MapCacheObject : CacheObject
    {
        private const int Timeout = 24 * 60;

        public MapCacheObject(int mapId)
        {
            TimeoutInMinutes = Timeout;
            Id = mapId;

            using (var entities = new MapsEntities())
            {
                Update(entities);
            }
        }

        public int Id { get; set; }

        public Dictionary<int, Node> Nodes { get; set; }

        public List<Edge> Edges { get; set; }

        public List<Floor> Floors { get; set; }

        public Dictionary<int, Graph> GraphsForFloor { get; set; }

        public FloydWarshallAllShortestPathAlgorithm<int, UndirectedEdge<int>> PathFinder { get; set; }

        public void UpdateGraph(MapsEntities entities)
        {
            Nodes = GraphService.GetNodesDictionary(entities, Id);
            Edges = GraphService.GetEdgeList(entities, Id);
            GraphsForFloor = GraphService.GetAllGraphs(entities, Floors.Select(f => f.Id));
            PathFinder = NavigationService.ComputeShortestPaths(Nodes, Edges);
        }

        public void UpdateFloors(MapsEntities entities)
        {
            Floors = FloorService.GetFloorsForMap(entities, Id);
        }

        private void Update(MapsEntities entities)
        {
            UpdateFloors(entities);
            UpdateGraph(entities);
        }
    }
}