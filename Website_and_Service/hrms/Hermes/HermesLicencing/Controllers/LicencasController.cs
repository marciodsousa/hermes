using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using HermesLicencing.Models;

namespace HermesLicencing.Controllers
{
    public class LicencasController : ApplicationController
    {
        //
        // GET: /Licencas/
        Models.LicencingDBEntities db = new Models.LicencingDBEntities();

        public ActionResult Index()
        {
            //return RespondTo(format =>
            //{
            //    format.Default = View();
            //    format.Json = () => Json(Models.TLicenca.All(), JsonRequestBehavior.AllowGet);
            //});
            return Json(Models.TLicenca.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int Id)
        {
            var licenca = Models.TLicenca.GetById(Id);

            if (licenca == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                //return RespondTo(format =>
                //{
                //    format.Default = View(licenca);
                //    format.Json = () => Json(licenca, JsonRequestBehavior.AllowGet);
                //});
                return Json(licenca, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(int id, DateTime dta, int idTipoLic, string status)
        {
            ActionResult result = null;

            TLicenca lic = new TLicenca { idLicenca = id,
                dataInicio = dta,
                idTipoLicenca = idTipoLic,
                estado = status,
            };
            switch (TLicenca.Update(lic))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TLicenca.GetById(id));
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

        public ActionResult Create(TLicenca lic)
        {
            int licId;
            licId = TLicenca.AddLicenca(lic);
            return Json(TLicenca.GetById(licId));
        }

    }
}
