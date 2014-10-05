using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClientWebService.Controllers
{
    public class GuiasTransporteController : Controller
    {
        //
        // GET: /GuiasTransporte/

        public ActionResult Index(int Id = 0)
        {
            var licsEmp = TGuiaTransporte.All();

            return Json(licsEmp, JsonRequestBehavior.AllowGet);
            
        }

        public ActionResult Show(int Id)
        {
            //var guia = TGuiaTransporte.GetById(Id);

            //if (guia == null)
            //{
            //    return new HttpNotFoundResult();
            //}
            //else
            //{
            //    return Json(guia, JsonRequestBehavior.AllowGet);
            //}

            var licsEmp = TGuiaTransporte.GetByUsr(Id);

            if (licsEmp == null)
                return new HttpNotFoundResult();
            else
                return Json(licsEmp, JsonRequestBehavior.AllowGet);
        }

        public ActionResult Update(TGuiaTransporte guia)
        {
            if(guia.idEmissao==-1)
            {
                emitGuia(TGuiaTransporte.GetById(guia.idGuia));
            }
            ActionResult result = null;

            switch (TGuiaTransporte.Update(guia))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TGuiaTransporte.GetById(guia.idGuia));
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

        public ActionResult Create(TGuiaTransporte guia)
        {
            int guiaId;
            guiaId = TGuiaTransporte.AddGuia(guia);
            return Json(TGuiaTransporte.GetById(guiaId));
        }

        private Boolean emitGuia(TGuiaTransporte guia)
        {
            Boolean ret = false;

            //vai aqui o código que permite submeter a guia à AT.

            //https://servicos.portaldasfinancas.gov.pt:701/sgdtws/documentosTransporte
            return ret;
        }

    }
}
