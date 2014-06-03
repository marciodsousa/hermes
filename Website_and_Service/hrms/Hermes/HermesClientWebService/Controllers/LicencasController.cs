using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;
using System.Net;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Text;

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
            int licId;
            //se licença para este IMEI já está atribuida, devolver
            var existLic = TLicenca.GetByIMEI(lic.imei);
            if (existLic != null)
                return Json(existLic);


            int idEmp = lic.idEmpresa;

            //caso contrário, fazer pedido de licença á empresa fornecedora


            //// corrected to WebRequest from HttpWebRequest
            //var url = "http://wvm100.dei.isep.ipp.pt/Hermes/Licenca";

            //// Synchronous Consumption
            //var syncClient = new WebClient();
            //var content = syncClient.DownloadString(url);
 
            //// Create the Json serializer and parse the response
            //DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(TLicenca));
            //using (var ms = new MemoryStream(Encoding.Unicode.GetBytes(content)))
            //{
            //    var weatherData = (TLicenca)serializer.ReadObject(ms);
            //}

            //*************
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/Hermes/Licencas");
            request.Method = "POST";

            System.Text.UTF8Encoding encoding = new System.Text.UTF8Encoding();
            Byte[] byteArray = encoding.GetBytes(Json(lic).ToString());

            request.ContentLength = byteArray.Length;
            request.ContentType = @"application/json";

            using (Stream dataStream = request.GetRequestStream())
            {
                dataStream.Write(byteArray, 0, byteArray.Length);
            }
            long length = 0;
            try
            {
                using (HttpWebResponse response = (HttpWebResponse)request.GetResponse())
                {
                    length = response.ContentLength;

                    // Create the Json serializer and parse the response
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(TLicenca));
                    using (var ms = new MemoryStream(Encoding.Unicode.GetBytes(response.ToString())))
                    {
                        var newLicence = (TLicenca)serializer.ReadObject(ms);
                        licId = TLicenca.AddLicenca(newLicence);

                        return Json(TLicenca.GetById(licId));
                    }
                }
            }
            catch (WebException ex)
            {
                ex.ToString();
                return new HttpStatusCodeResult(409);
            }

        }

       

    }
}
