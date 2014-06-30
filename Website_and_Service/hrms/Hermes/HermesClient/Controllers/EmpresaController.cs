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
    public class EmpresaController : Controller
    {
        //
        // GET: /Empresa/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            return RedirectToAction("Edit", "Empresa");
        }


        [HttpGet]
        public ActionResult Edit()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var emps = TEmpresa.All();
            TEmpresa emp;

            if (emps.Count() < 1)
                return RedirectToAction("Login", "Utilizador");
            emp = emps.FirstOrDefault();

            return View(emp);
        }

        [HttpPost]
        public ActionResult Edit(Models.TEmpresa empresa)
        {
            if (UpdateCompanyServer(empresa))
            {
                TEmpresa.Update(empresa);
            }

            return RedirectToAction("Edit", "Empresa");
        }

        private Boolean UpdateCompanyServer(TEmpresa emp)
        {
            var serializer = new JavaScriptSerializer();
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/HermesLicencingWS/Empresas/" + emp.idEmpresa);
            //var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:49346/Empresas/" + emp.idEmpresa);

            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "PUT";


            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(Json(emp).Data);

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

    }
}
