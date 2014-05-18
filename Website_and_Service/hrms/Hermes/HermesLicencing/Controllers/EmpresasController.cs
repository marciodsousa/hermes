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

        public ActionResult Update(TEmpresa emp)
        {
            ActionResult result = null;

            switch (TEmpresa.Update(emp))
            {
                case 1: //empresa actualizada com sucesso
                    result = Json(TEmpresa.GetById(emp.idEmpresa));
                    break;

                case 0: //empresa não encontrada
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
