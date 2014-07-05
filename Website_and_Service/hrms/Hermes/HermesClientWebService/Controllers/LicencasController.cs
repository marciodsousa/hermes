using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;
using System.Net;
using System.IO;
using System.Web.Script.Serialization;
using System.Text;
using Newtonsoft.Json;

namespace HermesClientWebService.Controllers
{
    public class LicencasController : ApplicationController
    {
        //
        // GET: /Licencas/

        public ActionResult Index()
        {
            return Json(TLicenca.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var licenca = TLicenca.GetById(Id);

            if (licenca == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(licenca, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(TLicenca lic)
        {
            ActionResult result = null;

            switch (TLicenca.Update(lic))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TLicenca.GetById(lic.idLicenca));
                    break;

                case 0: //licenca não encontrada
                    result = new HttpNotFoundResult();
                    break;

                case 2: //dados inválidos
                    result = new HttpStatusCodeResult(400);
                    break;

                case 3:
                    result = new HttpStatusCodeResult(409);
                    break;
            }

            return result;
        }

        public ActionResult Create(TLicenca lic)
        {
            string result;
            var serializer = new JavaScriptSerializer();
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/HermesLicencingWS/Licencas");
            //var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:49346/licencas");

            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            TEmpresa emp = TEmpresa.All().First();
            lic.idEmpresa = emp.idEmpresa;

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(Json(lic).Data);

                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();

                var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

                //caso retorne codigo 409, quer dizer que valor maximo de licenças foi atingido. Retornar.
                if(httpResponse.StatusCode==HttpStatusCode.Conflict)
                    return new HttpStatusCodeResult(409);

                //senão prosseguir parsing.
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    result = streamReader.ReadToEnd();
                }
            }

            TLicenca licence = serializer.Deserialize<TLicenca>(result);
            //licence.TEmpresa = null;

            if ((lic.imei == licence.imei) && (TLicenca.GetByIMEI(licence.imei)== null))
                TLicenca.AddLicenca(licence);
            
            return Json(TLicenca.GetByIMEI(licence.imei), JsonRequestBehavior.AllowGet);
        }
    }
}
