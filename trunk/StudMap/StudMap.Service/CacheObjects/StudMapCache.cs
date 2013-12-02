﻿using StudMap.Data.Entities;
using System;
using System.Web;
using System.Web.Caching;

namespace StudMap.Service.CacheObjects
{
    public class StudMapCache
    {
        public static GlobalCacheObject Global
        {
            get
            {
                return GetGlobalCache();
            }
        }

        public static MapCacheObject Map(int mapId)
        {
            return GetMapCache(mapId);
        }

        public static FingerprintCacheObject Fingerprint(int mapId)
        {
            return GetFingerprintCache(mapId);
        }

        public static void UpdateFloors(MapsEntities entities, int mapId)
        {
            Global.UpdateFloors(entities);
            Map(mapId).UpdateFloors(entities);
        }

        public static void UpdateGraph(MapsEntities entities, int mapId)
        {
            Map(mapId).UpdateGraph(entities);
        }

        public static void UpdateFingerprints(MapsEntities entities, int mapId)
        {
            Fingerprint(mapId).Update(entities);
        }

        /// <summary>
        /// Globale Daten werden unter diesem Schlüssel gecachet.
        /// </summary>
        private const string GlobalCacheKey = "Global";

        /// <summary>
        /// Für jede Map wird ein Cache-Eintrag einglegt, dessen 
        /// Zugriffsschlüssel dem hier definierten Format entspricht.
        /// {0} wird mit der entsprechenden Map-ID ersetzt.
        /// </summary>
        private const string MapCacheKey = "Map:{0}";

        /// <summary>
        /// Für jede Map wird ein Cache-Eintrag einglegt, dessen 
        /// Zugriffsschlüssel dem hier definierten Format entspricht.
        /// {0} wird mit der entsprechenden Map-ID ersetzt.
        /// </summary>
        private const string FingerprintCacheKey = "Fingerprint:{0}";

        private static GlobalCacheObject GetGlobalCache()
        {
            var cacheObject = (GlobalCacheObject)HttpRuntime.Cache.Get(GlobalCacheKey) ??
                              (GlobalCacheObject)RegisterObject(GlobalCacheKey, () => new GlobalCacheObject());

            return cacheObject;
        }

        private static MapCacheObject GetMapCache(int mapId)
        {
            string cacheKey = String.Format(MapCacheKey, mapId);
            var cacheObject = (MapCacheObject)HttpRuntime.Cache.Get(cacheKey) ??
                              (MapCacheObject)RegisterObject(cacheKey, () => new MapCacheObject(mapId));

            return cacheObject;
        }

        private static FingerprintCacheObject GetFingerprintCache(int mapId)
        {
            string cacheKey = String.Format(FingerprintCacheKey, mapId);
            var cacheObject = (FingerprintCacheObject)HttpRuntime.Cache.Get(cacheKey) ??
                              (FingerprintCacheObject)RegisterObject(cacheKey, () => new FingerprintCacheObject(mapId));

            return cacheObject;
        }

        private static object RegisterObject(string key, Func<CacheObject> createObject)
        {
            var cacheObject = createObject();
            HttpRuntime.Cache.Insert(key, cacheObject, null,
                DateTime.Now.AddMinutes(cacheObject.TimeoutInMinutes),
                Cache.NoSlidingExpiration, CacheItemPriority.High,
                (k, v, r) =>
                {
                    // Wenn das Objekt manuell gelöscht wurde, bedeutet das,
                    // dass es neu angelegt werden muss, da Daten ungültig sind
                    if (r == CacheItemRemovedReason.Removed)
                        RegisterObject(key, createObject);
                });
            return cacheObject;
        }
    }
}