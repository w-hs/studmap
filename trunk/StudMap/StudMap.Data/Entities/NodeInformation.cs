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
    
    public partial class NodeInformation
    {
        public int Id { get; set; }
        public int NodeId { get; set; }
        public string DisplayName { get; set; }
        public string RoomName { get; set; }
        public string QRCode { get; set; }
        public string NFCTag { get; set; }
        public Nullable<int> PoiId { get; set; }
        public System.DateTime CreationTime { get; set; }
    
        public virtual Nodes Nodes { get; set; }
        public virtual PoIs PoIs { get; set; }

        public NodeInformation() {
            DisplayName = String.Empty;
            RoomName = String.Empty;
            QRCode = String.Empty;
            NFCTag = String.Empty;
        }
    }
}
