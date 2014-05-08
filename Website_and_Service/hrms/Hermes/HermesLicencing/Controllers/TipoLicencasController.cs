using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencing.Models;

namespace HermesLicencing.Controllers
{
    public class TipoLicencasController : ApplicationController
    {
        //
        // GET: /TipoLicencas/

        public ActionResult Index()
        {
            //return RespondTo(format =>
            //{
            //    //format.Default = View();
            //    format.Json = () => Json(Models.TTipoLicenca.All(), JsonRequestBehavior.AllowGet);
            //});
            return Json(Models.TTipoLicenca.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var tipoLic = Models.TTipoLicenca.GetById(Id);

            if (tipoLic == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                //return RespondTo(format =>
                //{
                //    //format.Default = View(tipoLic);
                //    format.Json = () => Json(tipoLic, JsonRequestBehavior.AllowGet);
                //});
                return Json(tipoLic, JsonRequestBehavior.AllowGet);
            }
        }
        /*
        public ActionResult Destroy(int UserId, int Id)
        {
            ActionResult result = null;

            switch (Users.GetById(UserId).DeleteAccount(Id))
            {
                case AccountDeleteResult.Deleted:
                    result = Json(new { success = true });
                    break;

                case AccountDeleteResult.NoSuchAccount:
                    result = new HttpNotFoundResult();
                    break;
            }

            return result;
        }*/

        public ActionResult Update(int id, string nome, string descricao, int duracao, decimal valor)
        {
            ActionResult result = null;

            TTipoLicenca tipoLic = new TTipoLicenca
            {
                idTipoLicenca = id,
                nome = nome,
                descricao = descricao,
                duracao = duracao,
                valor = valor
            };
            switch (TTipoLicenca.Update(tipoLic))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TTipoLicenca.GetById(id));
                    break;

                case 0: //utilizador não encontrado
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

        public ActionResult Create(TTipoLicenca tipoLic)
        {
            int tipoId;
            tipoId = TTipoLicenca.AddLicType(tipoLic);
            return Json(TTipoLicenca.GetById(tipoId));
        }

    }
}
