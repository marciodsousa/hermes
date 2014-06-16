using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencingInterface.Models;

namespace HermesLicencing.Controllers
{
    public class LicencasController : ApplicationController
    {
        //
        // GET: /Licencas/

        public ActionResult Index()
        {
            return Json(TLicenca.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var licenca = TLicenca.GetById(Id);

            if (licenca == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(licenca, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(TLicenca lic)
        {
            ActionResult result = null;

            switch (TLicenca.Update(lic))
            {
                case 1: //licença actualizada com sucesso
                    result = Json(TLicenca.GetById(lic.idLicenca));
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

        public ActionResult Create(TLicenca lic)
        {
            return new HttpStatusCodeResult(400);
            //se licença para este IMEI já está atribuida, devolver
            var existLic = TLicenca.GetByIMEI(lic.imei);
            if (existLic != null)
                return Json(existLic, JsonRequestBehavior.AllowGet);


            var emp = TEmpresa.GetById(lic.idEmpresa);
            if (emp != null)
            {
              
                //caso contrário, ver se empresa já tem o número máximo de licenças atribuido
                if (TLicenca.GetByEmp(emp.idEmpresa).Count() < emp.maxRegs)
                {
                    //numero maximo de licenças da empresa ainda nao foi atingido, adicionar.
                    int licId = TLicenca.AddLicenca(lic);
                    return Json(TLicenca.GetById(licId), JsonRequestBehavior.AllowGet);
                }
            }

            //Enviar Aviso ao admin a dizer que nr máximo de licenças foi atingido
            return new HttpStatusCodeResult(409);
    
        }

    }
}
