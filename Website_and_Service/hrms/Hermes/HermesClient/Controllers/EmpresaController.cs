using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesLicencingInterface.Controllers
{
    public class EmpresaController : Controller
    {
        //
        // GET: /Empresa/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TEmpresa.All();
            return View(ret);
        }

        public ActionResult Edit(int id = 0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TEmpresa emp = TEmpresa.GetById(id);
            if (emp == null)
                return HttpNotFound();

            return View(emp);
        }

        [HttpPost]
        public ActionResult Edit(TEmpresa empresa)
        {
            TEmpresa.Update(empresa);

            return RedirectToAction("Index", "Empresa");
        }

    }
}
