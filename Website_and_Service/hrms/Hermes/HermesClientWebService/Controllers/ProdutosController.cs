using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;
using System.Net;
using System.IO;
using System.Web.Script.Serialization;
using System.Text;
using Newtonsoft.Json;

namespace HermesClientWebService.Controllers
{
    public class ProdutosController : ApplicationController
    {
        //
        // GET: /Licencas/

        public ActionResult Index()
        {
            return Json(TProduto.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var produto = TProduto.GetById(Id);

            if (produto == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(produto, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(TProduto prod)
        {
            ActionResult result = null;

            switch (TProduto.Update(prod))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TProduto.GetById(prod.idProduto));
                    break;

                case 0: //licenca não encontrada
                    result = new HttpNotFoundResult();
                    break;

                case 2: //dados inválidos
                    result = new HttpStatusCodeResult(400);
                    break;

                case 3:
                    result = new HttpStatusCodeResult(409);
                    break;
            }

            return result;
        }

        public ActionResult Create(TProduto prod)
        {
            int prodId;
            var prodDB = TProduto.GetByCodProd(prod.codProduto);

            if (prodDB == null)
                prodId = TProduto.AddProduto(prod);
            else
                prodId = prodDB.idProduto;
            return Json(TProduto.GetById(prodId));
        }
    }
}
