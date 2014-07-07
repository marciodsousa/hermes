using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using System.Web.Script.Serialization;
using HermesClient.Models;
using System.IO;
using Newtonsoft.Json;

namespace HermesClientWebService.Controllers
{
    public class AuthController : ApplicationController
    {

        //
        // GET: /Licencas/

        //public ActionResult Index()
        //{

        //    string result;
        //    var serializer = new JavaScriptSerializer();
        //    var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://wvm100.dei.isep.ipp.pt/HermesClientWS/Clientes");

        //    //var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:61098/Clientes");

        //    httpWebRequest.ContentType = "application/json";
        //    httpWebRequest.Method = "POST";

        //    TCliente prod = new TCliente();
        //    prod.contacto = "227895684";
        //    prod.nif = "222222222";
        //    prod.idCliente = 0;
        //    prod.nome = "Uva";


        //    using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
        //    {
        //        string json = JsonConvert.SerializeObject(Json(prod).Data);
        //        json = "{\"contacto\":\"223659854\",\"nome\":\"Isep\",\"nif\":\"524136985\",\"idCliente\":0}";
        //        streamWriter.Write(json);
        //        streamWriter.Flush();
        //        streamWriter.Close();

        //        var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();

        //        //caso retorne codigo 409, quer dizer que valor maximo de licenças foi atingido. Retornar.
        //        if (httpResponse.StatusCode == HttpStatusCode.Conflict)
        //            return new HttpStatusCodeResult(409);

        //        //senão prosseguir parsing.
        //        using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
        //        {
        //            result = streamReader.ReadToEnd();
        //        }
        //    }

        //    return new HttpStatusCodeResult(409);
        //}

        public ActionResult Create(string usr, string passwd)
        {
            return Json(UtilizadoresController.IsValid(usr, passwd));
        }

    }
}
