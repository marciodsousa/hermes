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
                utilizadores.Only("index", "show", "create", "update");
            });

            map.Resources<EmpresasController>(empresas =>
            {
                empresas.Only("index", "show", "create", "update");
            });

            map.Resources<LicencasController>(licencas =>
            {
                licencas.Only("index", "show", "create", "update");
            });

            map.Resources<TipoLicencasController>(tipolicencas =>
            {
                tipolicencas.Only("index", "show", "create", "update", "destroy");
            });

            map.Resources<TipoUtilizadoresController>(tipoutilizadores =>
            {
                tipoutilizadores.Only("index", "show", "create", "update", "destroy");
            });
            map.Resources<AuthController>(auths =>
            {
                auths.Only("index", "show", "create", "update", "destroy");
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