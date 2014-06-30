using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClient.Controllers
{
    public class ClienteController : Controller
    {
        //
        // GET: /Cliente/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TCliente.All();
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
        public ActionResult Create(Models.TCliente cliente)
        {
            using (var db = new PESTICliEntities())
            {
                var sysCli = db.TCliente.Create();

                sysCli.nome = cliente.nome;
                sysCli.contacto = cliente.contacto;
                sysCli.nif = cliente.nif;

                db.TCliente.Add(sysCli);
                db.SaveChanges();

                return RedirectToAction("Index", "Cliente");
            }
        }

        public ActionResult Edit(int id = 0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TCliente cli = TCliente.GetById(id);
            if (cli == null)
                return HttpNotFound();

            return View(cli);
        }

        [HttpPost]
        public ActionResult Edit(TCliente cliente)
        {
            TCliente.Update(cliente);

            return RedirectToAction("Index", "Cliente");
        }


        public ActionResult Delete()
        {
            return View();
        }

    }
}
