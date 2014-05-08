using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace HermesLicencing.Controllers
{
    public class AuthController : ApplicationController
    {

        public ActionResult Create(string usr, string passwd)
        {
            return Json(UtilizadoresController.IsValid(usr, passwd));
        }

    }
}
