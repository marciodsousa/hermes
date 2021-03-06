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

    public partial class TProduto
    {
        public int idProduto { get; set; }

        [Required]
        [StringLength(50)]
        [Display(Name = "Nome do Produto: ")]
        public string nome { get; set; }

        [Required]
        [Display(Name = "Valor Unit�rio (em centimos): ")]
        public int valUnitario { get; set; }


        [StringLength(50)]
        [Display(Name = "C�digo do produto: ")]
        public string codProduto { get; set; }


        [StringLength(200)]
        [Display(Name = "Descri��o: ")]
        public string descricao { get; set; }

        public int estado { get; set; }

        public static List<TProduto> All()
        {
            var db = new Models.PESTICliEntities();

            var query = db.TProduto.Select(c => c);
            return query.ToList();
        }

        public static TProduto GetById(int id)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TProduto.Where(c => c.idProduto == id).Select(c => c);

            var list = query.ToList();
            if (list.Count > 0)
                return query.ToList().First();

            return null;
        }

        public static TProduto GetByCodProd(string codProd)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TProduto.Where(c => c.codProduto.ToLower().Contains(codProd.ToLower())).Select(c => c);

            var list = query.ToList();
            if (list.Count > 0)
                return query.ToList().First();

            return null;
        }

        public static int Update(TProduto prod)
        {
            int ret = 0;
            var db = new Models.PESTICliEntities();

            var query = db.TProduto.Select(c => c);
            var prods = query.ToList();

            if (!ValidateData(prod))
                return 2;

            foreach (TProduto p in prods)
            {
                if (prod.idProduto == p.idProduto)
                {
                    p.idProduto = prod.idProduto;
                    p.nome = prod.nome;
                    p.codProduto = prod.codProduto;
                    p.descricao = prod.descricao;
                    p.valUnitario = prod.valUnitario;
                    p.estado = prod.estado;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddProduto(TProduto prod)
        {
            var db = new Models.PESTICliEntities();

            if (!ValidateData(prod))
                return 2;

            db.TProduto.Add(prod);
            db.SaveChanges();

            return prod.idProduto;
        }

        public static bool ValidateData(TProduto prod)
        {
            bool ret = true;


            if (prod.descricao == null || prod.descricao.CompareTo("") == 0)
                return false;
            if (prod.nome == null && prod.nome.CompareTo("") == 0)
                return false;
            if (prod.valUnitario == null)
                return false;
            if (prod.codProduto == null || prod.codProduto.CompareTo("") == 0)
                return false;

            return ret;
        }
    }
}
