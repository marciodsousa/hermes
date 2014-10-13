using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClient.Controllers
{
    public class LocalController : Controller
    {
        //
        // GET: /Local/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TLocal.All();
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
        public ActionResult Create(Models.TLocal local)
        {
            using (var db = new PESTICliEntities())
            {
                var sysLoc = db.TLocal.Create();

                sysLoc.nome = local.nome;
                sysLoc.morada = local.morada;

                db.TLocal.Add(sysLoc);
                db.SaveChanges();

                return RedirectToAction("Index", "Local");
            }
        }

        public ActionResult Edit(int id = 0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TLocal prod = TLocal.GetById(id);
            if (prod == null)
                return HttpNotFound();

            return View(prod);
        }

        [HttpPost]
        public ActionResult Edit(TLocal loc)
        {
            TLocal.Update(loc);

            return RedirectToAction("Index", "Local");
        }

        public ActionResult Disable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var loc = TLocal.GetById(id);

            if (loc != null)
            {
                loc.estado = 1;
                TLocal.Update(loc);
            }

            return RedirectToAction("Index", "Licenca");
        }

        public ActionResult Enable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var loc = TLocal.GetById(id);

            if (loc != null)
            {
                loc.estado = 1;
                TLocal.Update(loc);
            }

            return RedirectToAction("Index", "Licenca");
        }

    }
}
