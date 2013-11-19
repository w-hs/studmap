﻿//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System.Data.Entity.Core.Objects;

namespace StudMap.Data.Entities
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    using System.Data.Objects.DataClasses;
    using System.Linq;
    
    public partial class MapsEntities : DbContext
    {
        public MapsEntities()
            : base("name=MapsEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public DbSet<Edges> Edges { get; set; }
        public DbSet<Floors> Floors { get; set; }
        public DbSet<Graphs> Graphs { get; set; }
        public DbSet<Maps> Maps { get; set; }
        public DbSet<NodeInformation> NodeInformation { get; set; }
        public DbSet<Nodes> Nodes { get; set; }
        public DbSet<PoIs> PoIs { get; set; }
        public DbSet<PoiTypes> PoiTypes { get; set; }
        public DbSet<NodeInformationForMap> NodeInformationForMap { get; set; }
    
        public virtual int DeleteFloor(Nullable<int> floorId)
        {
            var floorIdParameter = floorId.HasValue ?
                new ObjectParameter("FloorId", floorId) :
                new ObjectParameter("FloorId", typeof(int));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("DeleteFloor", floorIdParameter);
        }
    
        public virtual int DeleteMap(Nullable<int> mapId)
        {
            var mapIdParameter = mapId.HasValue ?
                new ObjectParameter("MapId", mapId) :
                new ObjectParameter("MapId", typeof(int));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("DeleteMap", mapIdParameter);
        }
    }
}