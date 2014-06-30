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

        public ActionResult Index(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var licsEmp = TLicenca.GetByEmp(id);

            return View(licsEmp);
        }

        //public ActionResult Details(int id)
        //{
        //    if (Session["userID"] == null)
        //        return RedirectToAction("Login", "Utilizador");

        //    var licsEmp = TLicenca.GetByEmp(id);

        //    ViewBag.Message = TEmpresa.GetById(id).nome;

        //    return View(licsEmp);
        //}

        public ActionResult Disable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var lic = TLicenca.GetById(id);

            if (lic != null)
            {
                lic.estado = "0";
                TLicenca.Update(lic);
            }

            return RedirectToAction("Index", "Empresa");
        }

        public ActionResult Enable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var lic = TLicenca.GetById(id);

            if (lic != null)
            {
                lic.estado = "1";
                TLicenca.Update(lic);
            }

            return RedirectToAction("Index", "Empresa");
        }

        public ActionResult Delete(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            TLicenca.DeleteById(id);

            return RedirectToAction("Index", "Empresa");
        }
    }
}
