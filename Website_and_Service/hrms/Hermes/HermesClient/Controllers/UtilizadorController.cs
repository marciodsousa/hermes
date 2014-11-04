using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.Mvc;
using HermesClient.Models;

namespace HermesClient.Controllers
{
    public class UtilizadorController : Controller
    {
        //
        // GET: /Utilizador/

        public ActionResult Index()
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var ret = TUtilizador.All();
            return View(ret);
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(TUtilizador utilizador)
        {
            int usrId;


            usrId = IsValid(utilizador.username, utilizador.password);
            if (usrId>0)
            {
                Session["userID"] = usrId;
                return RedirectToAction("Index", "Produto");
            }
            else
            {
                ModelState.AddModelError("", "Login Data is incorrect.");

            }

            return View(utilizador);
        }

        public ActionResult Logout()
        {
            Session["userID"] = null;

            return RedirectToAction("Login", "Utilizador");
        }

        [HttpGet]
        public ActionResult Create()
        {
            if(Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            return View();
        }

        [HttpPost]
        public ActionResult Create(TUtilizador utilizador)
        {

            using (var db = new PESTICliEntities())
            {
                TEmpresa emp = TEmpresa.All().FirstOrDefault();
                utilizador.idEmpresa = emp.idEmpresa;

                var crypto = new SimpleCrypto.PBKDF2();
                var encryPass = crypto.Compute(utilizador.password);
                var sysUser = db.TUtilizador.Create();

                sysUser.username = utilizador.username;
                sysUser.nome = utilizador.nome;
                sysUser.email = utilizador.email;
                sysUser.password = encryPass;
                sysUser.passwordSalt = crypto.Salt;
                sysUser.tipoUtilizador = utilizador.tipoUtilizador;
                sysUser.estado = utilizador.estado;
                sysUser.idEmpresa = utilizador.idEmpresa;

                db.TUtilizador.Add(sysUser);
                db.SaveChanges();

                return RedirectToAction("Index", "Utilizador");

            }
        }

        public ActionResult Edit(int id = 0)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TUtilizador usr = TUtilizador.GetById(id);
            if (usr == null)
                return HttpNotFound();

            return View(usr);
        }

        [HttpPost]
        public ActionResult Edit(TUtilizador utilizador)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            TUtilizador.Update(utilizador);

            return RedirectToAction("Index", "Utilizador");
        }

        public ActionResult Disable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var usr = TUtilizador.GetById(id);

            if (usr != null)
            {
                usr.estado = 1;
                TUtilizador.Update(usr);

            }

            return RedirectToAction("Index", "Utilizador");
        }

        public ActionResult Enable(int id)
        {
            if (Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");

            var usr = TUtilizador.GetById(id);

            if (usr != null)
            {
                usr.estado = 0;
                TUtilizador.Update(usr);

            }

            return RedirectToAction("Index", "Utilizador");
        }

        private int IsValid(string username, string password)
        {
            var crypto = new SimpleCrypto.PBKDF2();

            int isValid = 0;

            using (var db = new PESTICliEntities())
            {
                var utilizador = db.TUtilizador.FirstOrDefault(u => u.username == username);
                if (utilizador != null)
                {
                    if (utilizador.tipoUtilizador != 0) //se nao for um motorista
                    {  
                        if (utilizador.password == crypto.Compute(password, utilizador.passwordSalt))
                            isValid = utilizador.idUtilizador; 
                    }
                }
                return isValid;
            }
        }
    }
}