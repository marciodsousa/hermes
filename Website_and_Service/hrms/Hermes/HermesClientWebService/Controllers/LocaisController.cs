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
    public class LocaisController : ApplicationController
    {
        //
        // GET: /Licencas/

        public ActionResult Index()
        {
            return Json(TLocal.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var local = TLocal.GetById(Id);

            if (local == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(local, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(TLocal loc)
        {
            ActionResult result = null;

            switch (TLocal.Update(loc))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TLocal.GetById(loc.idLocal));
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

        public ActionResult Create(TLocal loc)
        {
            int locId;
            var locDB = TLocal.GetByNome(loc.nome);

            if (locDB == null)
                locId = TLocal.AddLocal(loc);
            else
                locId = locDB.idLocal;
            return Json(TLocal.GetById(locId));
        }
    }
}
