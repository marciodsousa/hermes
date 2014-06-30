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

        public ActionResult Details(int id=0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TGuiaTransporte guia = TGuiaTransporte.GetById(id);
            if (guia == null)
                return RedirectToAction("Index", "GuiaTransporte");

            return View(guia);
        }

        public ActionResult Delete()
        {
            return View();
        }

    }
}
