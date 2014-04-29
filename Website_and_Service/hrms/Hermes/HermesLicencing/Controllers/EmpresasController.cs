using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

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

    }
}
