using System.Web.Routing;
using RestfulRouting;
using HermesLicencing.Controllers;
using System.Web.Mvc;

[assembly: WebActivator.PreApplicationStartMethod(typeof(HermesLicencing.Routes), "Start")]

namespace HermesLicencing
{
    public class Routes : RouteSet
    {
        public override void Map(IMapper map)
        {
            map.DebugRoute("routedebug");

            map.Route(new Route("",
                new RouteValueDictionary(new { controller = "Utilizadores", action = "Login" }),
                new MvcRouteHandler()));

            map.Resources<UtilizadoresController>(utilizadores =>
            {
                utilizadores.Only("index","show");
                utilizadores.Resources<EmpresasController>(contas =>
                {
                    contas.Only("index");
                });
            });

            map.Resources<UtilizadoresController>(utilizadores =>
            {
                utilizadores.Except("create", "destroy");
                utilizadores.Resources<EmpresasController>(empresas =>
                {
                    empresas.Only("index", "show", "destroy");
                    /*empresas.Resources<LicencasController>(licencas =>
                    {
                        licencas.Only("index", "create");
                    });*/
                });
                /*utilizadores.Resources<AccountsController>(accounts =>
                {
                    accounts.Only("index", "show", "destroy");
                    accounts.Resources<TransactionsController>(transactions =>
                    {
                        transactions.Only("index", "create");
                    });
                });*/
            });
            /*
             * TODO: Add your routes here.
             * 
            map.Root<HomeController>(x => x.Index());
            
            map.Resources<BlogsController>(blogs =>
            {
                blogs.As("weblogs");
                blogs.Only("index", "show");
                blogs.Collection(x => x.Get("latest"));

                blogs.Resources<PostsController>(posts =>
                {
                    posts.Except("create", "update", "destroy");
                    posts.Resources<CommentsController>(c => c.Except("destroy"));
                });
            });

            map.Area<Controllers.Admin.BlogsController>("admin", admin =>
            {
                admin.Resources<Controllers.Admin.BlogsController>();
                admin.Resources<Controllers.Admin.PostsController>();
            });
             */
        }

        public static void Start()
        {
            var routes = RouteTable.Routes;
            routes.MapRoutes<Routes>();
        }
    }
}