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
    public class ClientesController : ApplicationController
    {
        //
        // GET: /Licencas/

        public ActionResult Index()
        {
            return Json(TCliente.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var cliente = TCliente.GetById(Id);

            if (cliente == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(cliente, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(TCliente cli)
        {
            ActionResult result = null;

            switch (TCliente.Update(cli))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TCliente.GetById(cli.idCliente));
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

        public ActionResult Create(TCliente cli)
        {
            int cliId;
            cliId = TCliente.AddCliente(cli);
            return Json(TCliente.GetById(cliId));
        }
    }
}
