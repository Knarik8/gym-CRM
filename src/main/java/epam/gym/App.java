package epam.gym;

import epam.gym.config.AppConfig;
import epam.gym.config.JPAConfig;
import epam.gym.config.MainWebAppInitializer;
import epam.gym.config.WebConfig;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class App
{
    public static void main( String[] args )
    {

            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(WebConfig.class);
            context.register(JPAConfig.class);
            context.register(MainWebAppInitializer.class);
            context.register(AppConfig.class);
//            context.refresh();

    }

}
