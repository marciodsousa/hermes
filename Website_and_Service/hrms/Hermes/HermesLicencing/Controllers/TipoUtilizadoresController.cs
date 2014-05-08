using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencing.Models;

namespace HermesLicencing.Controllers
{
    public class TipoUtilizadoresController : ApplicationController
    {
        //
        // GET: /TipoUtilizadores/

        public ActionResult Index()
        {
            //return RespondTo(format =>
            //{
            //    //format.Default = View();
            //    format.Json = () => Json(Models.TTipoUtilizador.All(), JsonRequestBehavior.AllowGet);
            //});
            return Json(Models.TTipoUtilizador.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var tipoUsr = Models.TTipoUtilizador.GetById(Id);

            if (tipoUsr == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                //return RespondTo(format =>
                //{
                //    //format.Default = View(tipoUsr);
                //    format.Json = () => Json(tipoUsr, JsonRequestBehavior.AllowGet);
                //});
                return Json(tipoUsr, JsonRequestBehavior.AllowGet);
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

        public ActionResult Update(int id, string nome, string descricao)
        {
            ActionResult result = null;

            TTipoUtilizador tipoUsr = new TTipoUtilizador
            {
                idTipoUtilizador = id,
                nome = nome,
                descricao = descricao
            };
            switch (TTipoUtilizador.Update(tipoUsr))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TTipoUtilizador.GetById(id));
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

        public ActionResult Create(TTipoUtilizador tipoUsr)
        {
            int tipoId;
            tipoId = TTipoUtilizador.AddUserType(tipoUsr);
            return Json(TTipoUtilizador.GetById(tipoId));
        }
    }
}
