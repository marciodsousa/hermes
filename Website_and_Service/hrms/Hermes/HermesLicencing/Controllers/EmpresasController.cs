using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencing.Models;

namespace HermesLicencing.Controllers
{
    public class EmpresasController : ApplicationController
    {
        //
        // GET: /Empresas/

        public ActionResult Index()
        {
            return RespondTo(format =>
            {
                format.Default = View();
                format.Json = () => Json(Models.TEmpresa.All(), JsonRequestBehavior.AllowGet);
            });
        }

        public ActionResult Show(int Id)
        {
            var empresa = Models.TEmpresa.GetById(Id);
            TempData["idEmpresa"] = Id;
            if (empresa == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return RespondTo(format =>
                {
                    format.Default = View(empresa);
                    format.Json = () => Json(empresa, JsonRequestBehavior.AllowGet);
                });
                
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

        public ActionResult Update(int id, string nome, string morada, string email, string nif, string contacto, string servidor, int idLicenca)
        {
            ActionResult result = null;

            TEmpresa emp = new TEmpresa
            {
                idEmpresa = id,
                nome = nome,
                morada = morada,
                email = email,
                nif = nif,
                contacto = contacto,
                servidor = servidor,
                idLicenca = idLicenca
            };
            switch (TEmpresa.Update(emp))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TEmpresa.GetById(id));
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

        public ActionResult Create(TEmpresa emp)
        {
            int empId;
            empId = TEmpresa.AddCompany(emp);
            return Json(TEmpresa.GetById(empId));
        }
    }
}
