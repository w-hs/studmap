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
    
    public partial class Maps
    {
        public Maps()
        {
            this.Floors = new HashSet<Floors>();
            this.Graphs = new HashSet<Graphs>();
        }
    
        public int Id { get; set; }
        public string Name { get; set; }
        public System.DateTime CreationTime { get; set; }
    
        public virtual ICollection<Floors> Floors { get; set; }
        public virtual ICollection<Graphs> Graphs { get; set; }
    }
}