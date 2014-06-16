using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClient.Controllers
{
    public class GuiaTransporteController : Controller
    {
        //
        // GET: /GuiaTransporte/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TGuiaTransporte.All();
            return View(ret);
        }

        [HttpGet]
        public ActionResult Create()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            return View();
        }

        [HttpPost]
        public ActionResult Create(Models.TGuiaTransporte guiaT)
        {
            using (var db = new PESTICliEntities())
            {
                var sysGuiaT = db.TGuiaTransporte.Create();

                sysGuiaT.idEmissao = guiaT.idEmissao;
                sysGuiaT.idUtilizador = guiaT.idUtilizador;
                sysGuiaT.matricula = guiaT.matricula;
                sysGuiaT.idCLiente = guiaT.idCLiente;
                sysGuiaT.dataTransporte = guiaT.dataTransporte;
                sysGuiaT.idLocalCarga = guiaT.idLocalCarga;
                sysGuiaT.idLocalDescarga = guiaT.idLocalDescarga;
                sysGuiaT.estado = guiaT.estado;

                db.TGuiaTransporte.Add(sysGuiaT);
                db.SaveChanges();

                return RedirectToAction("Index", "GuiaTransporte");
            }
        }

        public ActionResult Edit()
        {
            return View();
        }

        public ActionResult Delete()
        {
            return View();
        }

    }
}
