using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencingInterface.Models;

namespace HermesLicencing.Controllers
{
    public class EmpresasController : ApplicationController
    {
        //
        // GET: /Empresas/

        public ActionResult Index()
        {
            return Json(TEmpresa.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var empresa = TEmpresa.GetById(Id);
            TempData["idEmpresa"] = Id;
            if (empresa == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(empresa, JsonRequestBehavior.AllowGet);
                
            }
        }

        public ActionResult Create(TEmpresa emp)
        {
            int empId;
            empId = TEmpresa.AddCompany(emp);
            return Json(TEmpresa.GetById(empId));
        }

        public ActionResult Update(TEmpresa emp)
        {

            var empServer = TEmpresa.GetById(emp.idEmpresa);

            //caso a empresa efectivamente já exista
            if (empServer != null)
            {
                //caso sejam dados de empresa submetidos pelo cliente (campo dos registos seja null)
                if (emp.maxRegs == null)
                    emp.maxRegs = empServer.maxRegs;

                TEmpresa.Update(emp);
            }
                
            return Json(TEmpresa.GetById(emp.idEmpresa));
        }
    }
}
