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
    
    public partial class AccessPointScans
    {
        public int Id { get; set; }
        public int FingerprintId { get; set; }
        public int AccessPointId { get; set; }
        public int RecievedSignalStrength { get; set; }
    
        public virtual AccessPoints AccessPoints { get; set; }
        public virtual Fingerprints Fingerprints { get; set; }
    }
}
