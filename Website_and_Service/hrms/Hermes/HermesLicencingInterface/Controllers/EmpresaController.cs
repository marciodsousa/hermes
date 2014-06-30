using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencingInterface.Models;

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

        [HttpGet]
        public ActionResult Create()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador"); 

            return View();
        }

        [HttpPost]
        public ActionResult Create(Models.TEmpresa empresa)
        {   
            using (var db = new LicencingDBEntities())
            {
                var sysEmp = db.TEmpresa.Create();

                sysEmp.nome = empresa.nome;
                sysEmp.morada = empresa.morada;
                sysEmp.email = empresa.email;
                sysEmp.nif = empresa.nif;
                sysEmp.contacto = empresa.contacto;
                sysEmp.maxRegs = empresa.maxRegs;

                db.TEmpresa.Add(sysEmp);
                db.SaveChanges();

                return RedirectToAction("Index", "Empresa");
            }
        }


        [HttpGet]
        public ActionResult Edit(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var emp = TEmpresa.GetById(id);
            return View(emp);
        }

        [HttpPost]
        public ActionResult Edit(Models.TEmpresa empresa)
        {
            TEmpresa.Update(empresa);

            return RedirectToAction("Index", "Empresa");
        }

        public ActionResult Delete()
        {
            return View();
        }

    }
}
