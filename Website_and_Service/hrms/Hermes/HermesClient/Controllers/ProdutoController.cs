using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClient.Controllers
{
    public class ProdutoController : Controller
    {
        //
        // GET: /Produto/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TProduto.All();
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
        public ActionResult Create(Models.TProduto produto)
        {
            using (var db = new PESTICliEntities())
            {
                var sysProd = db.TProduto.Create();

                sysProd.nome = produto.nome;
                sysProd.codProduto = produto.codProduto;
                sysProd.descricao = produto.descricao;
                sysProd.valUnitario = produto.valUnitario;

                db.TProduto.Add(sysProd);
                db.SaveChanges();

                return RedirectToAction("Index", "Produto");
            }
        }

        public ActionResult Edit(int id = 0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TProduto prod = TProduto.GetById(id);
            if(prod==null)
                return RedirectToAction("Index", "Produto");

            return View(prod);
        }

        [HttpPost]
        public ActionResult Edit(TProduto prod)
        {

            TProduto.Update(prod);

            return RedirectToAction("Index", "Produto");
        }

        public ActionResult Delete()
        {
            return View();
        }

    }
}
