using System;
using System.Reflection;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using Newtonsoft.Json;
using PubeoAPI.DTO;
using PubeoAPI.model;
using PubeoAPI.Repository;
using securityJWT.Options;
using AutoMapper;
using PubeoAPI.model.auth;
using Microsoft.AspNetCore.Mvc.Versioning;
using Swashbuckle.AspNetCore.SwaggerGen;
using Microsoft.AspNetCore.JsonPatch.Operations;

namespace PubeoAPI
{
    public class Startup
    {
        private readonly SymmetricSecurityKey _signingKey;
        private readonly string MyAllowSpecificOrigins = "_myAllowSpecificOrigins";
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
            _signingKey = new SymmetricSecurityKey(Encoding.ASCII.GetBytes(Configuration.GetValue("SecretKey","")));
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            var jwtAppSettingOptions = Configuration.GetSection(nameof(JwtIssuerOptions));

            services.Configure<JwtIssuerOptions>(options => 
            {
                options.Issuer = jwtAppSettingOptions[nameof(JwtIssuerOptions.Issuer)];
                options.Audience = jwtAppSettingOptions[nameof(JwtIssuerOptions.Audience)];
                options.SigningCredentials = new SigningCredentials(_signingKey,
                SecurityAlgorithms.HmacSha256);
            });

            var tokenValidationParameters = new TokenValidationParameters
            {
                ValidateIssuer = true,
                ValidIssuer = jwtAppSettingOptions[nameof(JwtIssuerOptions.Issuer)],

                ValidateAudience = true,
                ValidAudience = jwtAppSettingOptions[nameof(JwtIssuerOptions.Audience)],

                ValidateIssuerSigningKey = true,
                IssuerSigningKey = _signingKey,

                RequireExpirationTime = true,
                ValidateLifetime = true,

                ClockSkew = TimeSpan.Zero
            };

            services
                .AddAuthentication(
                    options =>
                    {
                        options.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
                    })
                .AddJwtBearer(JwtBearerDefaults.AuthenticationScheme, options =>
                    {
                        options.Audience = jwtAppSettingOptions[nameof(JwtIssuerOptions.Audience)];
                        options.ClaimsIssuer = jwtAppSettingOptions[nameof(JwtIssuerOptions.Issuer)];
                        options.TokenValidationParameters = tokenValidationParameters;
                        options.SaveToken = true;
                    });             

            services.AddOptions();
            services.AddCors(options =>
                {
                    options.AddPolicy(name: MyAllowSpecificOrigins,
                                    builder =>
                                    {
                                        builder.WithOrigins("http://localhost:4200").AllowAnyHeader().AllowAnyMethod();
                                    });
                });

            services.AddDbContext<PubeoAPIdbContext>(options =>
                options.UseSqlServer(Configuration.GetConnectionString("PubeoAppDB")));

            services.AddIdentity<User, Role>()
                .AddEntityFrameworkStores<PubeoAPIdbContext>()
                .AddDefaultTokenProviders();
            
            services.AddControllers();

            services.AddMvc(option => option.EnableEndpointRouting = false)
                .SetCompatibilityVersion(CompatibilityVersion.Version_3_0)
                .AddNewtonsoftJson(opt => opt.SerializerSettings.ReferenceLoopHandling = ReferenceLoopHandling.Ignore);

            services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());
            services.AddTransient<ILocalRepository, LocalRepository>();


            services.AddApiVersioning(cfg =>
            {
                cfg.DefaultApiVersion = new ApiVersion(2,0);
                cfg.AssumeDefaultVersionWhenUnspecified = true;
                cfg.ReportApiVersions = true;
            });

            services.AddSwaggerGen(options =>
            {
                options.SwaggerDoc("v1", new OpenApiInfo() { Title = "My API", Version = "v1" });
                options.SwaggerDoc("v2", new OpenApiInfo() { Title = "My API", Version = "v2" });
                options.OperationFilter<RemoveVersionFromParameter>();

                options.DocumentFilter<ReplaceVersionWithExactValueInPath>();

                options.DocInclusionPredicate((docName, apiDesc) =>
                {
                    if (!apiDesc.TryGetMethodInfo(out MethodInfo methodInfo)) return false;

                    var versions = methodInfo.DeclaringType
                        .GetCustomAttributes(true)
                        .OfType<ApiVersionAttribute>()
                        .SelectMany(attr => attr.Versions);

                    return versions.Any(v => $"v{v.ToString()}" == docName);
                });
                options.ResolveConflictingActions(apiDescriptions => apiDescriptions.First());

            });

        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            app.UseCors(MyAllowSpecificOrigins);

            app.UseHttpsRedirection();

            app.UseSwagger();

            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint($"/swagger/v1/swagger.json", $"v1");
                c.SwaggerEndpoint($"/swagger/v2/swagger.json", $"v2");
            });

            app.UseRouting();

            app.UseAuthentication();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        public class RemoveVersionFromParameter : IOperationFilter
        {
            public void Apply(OpenApiOperation operation, OperationFilterContext context)
            {
                var versionParameter = operation.Parameters.Single(p => p.Name == "version");
                operation.Parameters.Remove(versionParameter);
            }
        }

        public class ReplaceVersionWithExactValueInPath : IDocumentFilter
        {
            public void Apply(OpenApiDocument swaggerDoc, DocumentFilterContext context)
            {
                var paths = new OpenApiPaths();
                foreach (var path in swaggerDoc.Paths)
                {
                    paths.Add(path.Key.Replace("v{version}", swaggerDoc.Info.Version), path.Value);
                }
                swaggerDoc.Paths = paths;
            }
        }
        
    }
}
