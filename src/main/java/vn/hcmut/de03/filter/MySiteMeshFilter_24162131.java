package vn.hcmut.de03.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class MySiteMeshFilter_24162131 extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/admin/*", "/WEB-INF/decorators/admin.jsp")
               .addDecoratorPath("/*", "/WEB-INF/decorators/user.jsp")
               .addExcludedPath("/WEB-INF/*") // Tránh lỗi lặp decorator
               .addExcludedPath("/assets/*");
    }
}
