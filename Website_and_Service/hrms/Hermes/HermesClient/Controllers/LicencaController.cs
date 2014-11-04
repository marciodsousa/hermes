using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;
using System.Web.Script.Serialization;
using System.Net;
using System.IO;
using Newtonsoft.Json;

namespace HermesClient.Controllers
{
    public class LicencaController : Controller
    {
        //
        // GET: /Licenca/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TLicenca.All();
            return View(ret);
        }

        public ActionResult Disable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var lic = TLicenca.GetById(id);

            if (lic != null)
            {
                lic.estado = "0";
                if (UpdateLicenceServer(lic))
                {
                    TLicenca.Update(lic);
                }
            }

            return RedirectToAction("Index", "Licenca");
        }

        public ActionResult Enable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var lic = TLicenca.GetById(id);

            if (lic != null)
            {
                lic.estado = "1";
                if (UpdateLicenceServer(lic))
                {
                    TLicenca.Update(lic);
                }
            }

            return RedirectToAction("Index", "Licenca");
        }

        public ActionResult Delete(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            TLicenca.DeleteById(id);

            return RedirectToAction("Index", "Licenca");
        }

        private Boolean UpdateLicenceServer(TLicenca lic)
        {
            var serializer = new JavaScriptSerializer();
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/HermesLicencingWS/Licencas/" + lic.idLicenca);
            //var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:49346/Licencas/" + lic.idLicenca);

            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "PUT";


            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(Json(lic).Data);

                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

                //caso retorne codigo 200, quer dizer que correu tudo bem.
                if (httpResponse.StatusCode == HttpStatusCode.OK)
                    return true;
                else
                    return false;
            }
        }

        private Boolean DeleteLicenceServer(TLicenca lic)
        {
            var serializer = new JavaScriptSerializer();
            //var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/HermesLicencingWS/Licencas/" + lic.idLicenca);
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:49346/Licencas/" + lic.idLicenca);

            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "DELETE";

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

            //caso retorne codigo 200, quer dizer que correu tudo bem.
            if (httpResponse.StatusCode == HttpStatusCode.OK)
                return true;
            else
                return false;
        }
    }
}
