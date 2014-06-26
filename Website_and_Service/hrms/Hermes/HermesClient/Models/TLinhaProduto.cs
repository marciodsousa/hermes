//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace HermesClient.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.Linq;
    using System.Web.Mvc;

    public partial class TLinhaProduto
    {
        public int idGuia { get; set; }
        public int idProduto { get; set; }
        public int quantidade { get; set; }

        public virtual TProduto TProduto { get; set; }

        public static List<TLinhaProduto> All()
        {
            var db = new Models.PESTICliEntities();

            var query = db.TLinhaProduto.Select(c => c);
            return query.ToList();
        }

        public static TLinhaProduto GetByGuiaId(int id)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TLinhaProduto.Where(c => c.idGuia == id).Select(c => c);
            return query.ToList().First();
        }

        public static int Update(TLinhaProduto linhaprod)
        {
            int ret = 0;
            var db = new Models.PESTICliEntities();

            var query = db.TLinhaProduto.Select(c => c);
            var linhaprods = query.ToList();

            if (!ValidateData(linhaprod))
                return 2;

            foreach (TLinhaProduto l in linhaprods)
            {
                if (linhaprod.idGuia == l.idGuia && linhaprod.idProduto == l.idProduto)
                {
                    l.quantidade = linhaprod.quantidade;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddUser(TLinhaProduto linhaprod)
        {
            var db = new Models.PESTICliEntities();

            if (!ValidateData(linhaprod))
                return 2;

            db.TLinhaProduto.Add(linhaprod);
            db.SaveChanges();

            return linhaprod.idGuia;
        }

        public static bool ValidateData(TLinhaProduto linhaprod)
        {
            bool ret = true;


            if (linhaprod.quantidade > 0)
                return false;

            return ret;
        }
    }
}