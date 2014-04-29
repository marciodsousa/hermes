using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using System.Data.Entity.Validation;

namespace HermesLicencing.Controllers
{
    public class UtilizadoresController : ApplicationController
    {
        //
        // GET: /Utilizadores/

        Models.LicencingDBEntities db = new Models.LicencingDBEntities();

        public ActionResult Index()
        {
            return Json(Models.TUtilizador.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int id)
        {
            var user = Models.TUtilizador.GetById(id);
            if (user == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(user, JsonRequestBehavior.AllowGet);
            }
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(Models.TUtilizador user)
        {

            //if (ModelState.IsValid)
            //{
            if (IsValid(user.username, user.password))
            {
                Session["user"] = db.TUtilizador.FirstOrDefault(u => u.username == user.username);
                return RedirectToAction("Index", "Home");
            }
            else
            {
                ModelState.AddModelError("", "Login data is incorrect!");
            }
            //}
            //var errors = ModelState.Values.SelectMany(v => v.Errors);
            return View(user);
        }
        public ActionResult Logout()
        {
            FormsAuthentication.SignOut();
            return RedirectToAction("Index", "Home");
        }


        public bool IsValid(string _username, string _password)
        {
            var crypto = new SimpleCrypto.PBKDF2();

            bool isValid = false;

            using (var db = new Models.LicencingDBEntities())
            {
                var utilizador = db.TUtilizador.FirstOrDefault(u => u.username == _username);
                if (utilizador != null)
                {
                    if (utilizador.password == crypto.Compute(_password, utilizador.passwordSalt))
                    {
                        isValid = true;
                    }
                }
                return isValid;
            }
        }
    }
}