//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace HermesLicencingInterface.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.Linq;
    using System.Web.Mvc;

    public partial class TUtilizador
    {
        public int idUtilizador { get; set; }

        [Required]
        [StringLength(20)]
        [Display(Name = "Username: ")]
        public string username { get; set; }

        [Required]
        public string passwordSalt { get; set; }

        [Required]
        [StringLength(200)]
        [Display(Name = "Nome: ")]
        public string nome { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [StringLength(200, MinimumLength = 6)]
        [Display(Name = "Password: ")]
        public string password { get; set; }

        [Required]
        [EmailAddress]
        [StringLength(200)]
        [Display(Name = "Email: ")]
        public string email { get; set; }

        public static List<TUtilizador> All()
        {
            var db = new Models.LicencingDBEntities();

            var query = db.TUtilizador.Select(c => c);
            return query.ToList();
        }

        public static TUtilizador GetById(int id)
        {

            var db = new Models.LicencingDBEntities();

            var query = db.TUtilizador.Where(c => c.idUtilizador == id).Select(c => c);

            var list = query.ToList();
            if (list.Count > 0)
                return query.ToList().First();

            return null;
        }

        public static int Update(TUtilizador usr)
        {
            int ret = 0;
            var db = new Models.LicencingDBEntities();

            var query = db.TUtilizador.Select(c => c);
            var users = query.ToList();

            if (!ValidateData(usr))
                return 2;

            foreach (TUtilizador u in users)
            {
                if (usr.idUtilizador == u.idUtilizador)
                {
                    u.idUtilizador = usr.idUtilizador;
                    u.username = usr.username;
                    u.passwordSalt = usr.passwordSalt;
                    u.nome = usr.nome;
                    u.password = usr.password;
                    u.email = usr.email;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddUser(TUtilizador usr)
        {
            var db = new Models.LicencingDBEntities();
            var crypto = new SimpleCrypto.PBKDF2();

            if (!ValidateData(usr))
                return 2;

            //tratamento da password
            var encryPass = crypto.Compute(usr.password);
            var encrySalt = crypto.Salt;
            usr.password = encryPass;
            usr.passwordSalt = encrySalt;

            db.TUtilizador.Add(usr);
            db.SaveChanges();

            return usr.idUtilizador;
        }

        public static bool ValidateData(TUtilizador usr)
        {
            bool ret = true;

            if (usr.username == null || usr.username.CompareTo("") == 0)
                return false;
            if (usr.passwordSalt == null || usr.passwordSalt.CompareTo("") == 0)
                return false;
            if (usr.password == null || usr.password.CompareTo("") == 0)
                return false;
            if (usr.nome == null && usr.nome.CompareTo("") == 0)
                return false;
            if (usr.email == null || usr.email.CompareTo("") == 0)
                return false;

            return ret;
        }
    }


}
