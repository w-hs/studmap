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
    
    public partial class PoIs
    {
        public PoIs()
        {
            this.NodeInformation = new HashSet<NodeInformation>();
        }
    
        public int Id { get; set; }
        public int TypeId { get; set; }
        public string Description { get; set; }
    
        public virtual ICollection<NodeInformation> NodeInformation { get; set; }
        public virtual PoiTypes PoiTypes { get; set; }
    }
}
