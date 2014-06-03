using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencingInterface.Models;

namespace HermesLicencingInterface.Controllers
{
    public class LicencaController : Controller
    {
        //
        // GET: /Licenca/

        public ActionResult Index(int idEmp)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var licsEmp = TLicenca.GetByEmp(idEmp);

            return View(licsEmp);
        }

        public ActionResult Details(int idEmp)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var licsEmp = TLicenca.GetByEmp(idEmp);

            return View(licsEmp);
        }

        public ActionResult Edit(int idLic)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            return View();
        }

        public ActionResult Delete(int idLic)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            return View();
        }
    }
}
