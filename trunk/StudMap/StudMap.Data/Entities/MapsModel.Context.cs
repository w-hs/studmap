﻿//------------------------------------------------------------------------------
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
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
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
    }
}