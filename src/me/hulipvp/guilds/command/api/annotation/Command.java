package me.hulipvp.guilds.command.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	
    /**
     * The name of the command
     * @return
     */
    String name() default "";

    /**
     * Useable aliases of the command
     * @return
     */
    String[] aliases() default {};

    /**
     * Description of the command
     * @return
     */
    String desc() default "Default description.";

    /**
     * Useage of the command
     * @return
     */
    String usage() default "/<command>";
    
    /**
     * Useable by players only
     * @return
     */
    boolean playerOnly() default false;
    
    /**
     * Useable by anyone on the server, no permission needed
     * @return
     */
    boolean isPublic() default false;
    
}