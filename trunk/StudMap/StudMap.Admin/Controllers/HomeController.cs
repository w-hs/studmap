﻿using System.Web;
using System.Web.Mvc;

namespace StudMap.Admin.Controllers
{
    public class HomeController : Controller
    {
        private readonly string _serverUploadFolder = System.Web.HttpContext.Current.Server.MapPath("~/App_Data");

        public ActionResult Index()
        {
            return View();
        }

        [Authorize(Roles = "Admins")]
        public ActionResult Admin()
        {
            return View();
        }

        [Authorize(Roles = "Admins")]
        [HttpPost]
        public void UploadMap(HttpPostedFileBase data)
        {
            if (data != null)
            {
                data.SaveAs(_serverUploadFolder + "\\" + data.FileName);
            }
        }

        //Zu Testzwecken (später besser in die API)
        public ContentResult GetMapData()
        {
            const string data = "{\"heatmap\":{\"binSize\":3,\"units\":\"\u00B0C\",\"map\":[{\"x\":21,\"y\":12,\"value\":20.2},{\"x\":24,\"y\":12,\"value\":19.9},{\"x\":27,\"y\":12,\"value\":19.7},{\"x\":30,\"y\":12,\"value\":19.7},{\"x\":21,\"y\":15,\"value\":20.5},{\"x\":24,\"y\":15,\"value\":19.3},{\"x\":27,\"y\":15,\"value\":19.4},{\"x\":30,\"y\":15,\"value\":19.9},{\"value\":19.9,\"points\":[{\"x\":2.513888888888882,\"y\":8.0},{\"x\":6.069444444444433,\"y\":8.0},{\"x\":6.069444444444434,\"y\":5.277535934291582},{\"x\":8.20833333333332,\"y\":2.208151950718685},{\"x\":13.958333333333323,\"y\":2.208151950718685},{\"x\":16.277777777777825,\"y\":5.277535934291582},{\"x\":16.277777777777803,\"y\":10.08151950718685},{\"x\":17.20833333333337,\"y\":10.012135523613962},{\"x\":17.27777777777782,\"y\":18.1387679671458},{\"x\":2.513888888888882,\"y\":18.0}]}]},\"overlays\":{\"polygons\":[{\"id\":\"p1\",\"name\":\"kitchen\",\"points\":[{\"x\":2.513888888888882,\"y\":8.0},{\"x\":6.069444444444433,\"y\":8.0},{\"x\":6.069444444444434,\"y\":5.277535934291582},{\"x\":8.20833333333332,\"y\":2.208151950718685},{\"x\":13.958333333333323,\"y\":2.208151950718685},{\"x\":16.277777777777825,\"y\":5.277535934291582},{\"x\":16.277777777777803,\"y\":10.08151950718685},{\"x\":17.20833333333337,\"y\":10.012135523613962},{\"x\":17.27777777777782,\"y\":18.1387679671458},{\"x\":2.513888888888882,\"y\":18.0}]}]},\"vectorfield\":{\"binSize\":3,\"units\":\"ft/s\",\"map\":[{\"x\":18,\"y\":21,\"value\":{\"x\":4,\"y\":3}},{\"x\":21,\"y\":21,\"value\":{\"x\":3,\"y\":3}},{\"x\":18,\"y\":24,\"value\":{\"x\":1,\"y\":2}},{\"x\":21,\"y\":24,\"value\":{\"x\":-3,\"y\":4}},{\"x\":24,\"y\":24,\"value\":{\"x\":-4,\"y\":1}}]},\"pathplot\":[{\"id\":\"flt-1\",\"classes\":\"planned\",\"points\":[{\"x\":23.8,\"y\":30.6},{\"x\":19.5,\"y\":25.7},{\"x\":14.5,\"y\":25.7},{\"x\":13.2,\"y\":12.3}]}]}";
            return Content(data, "application/json");
        }
    }
}
