//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace StudMap.Data.Entities
{
    using System;
    using System.Collections.Generic;
    
    public partial class RSSDistribution
    {
        public int NodeId { get; set; }
        public int AccessPointId { get; set; }
        public Nullable<int> AvgRSS { get; set; }
        public Nullable<double> StDevRSS { get; set; }
    }
}
