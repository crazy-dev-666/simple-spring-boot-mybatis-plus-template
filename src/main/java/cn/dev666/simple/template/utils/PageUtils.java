package cn.dev666.simple.template.utils;

        import cn.dev666.simple.template.obj.common.Page;
        import cn.dev666.simple.template.obj.common.Pageable;
        import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 简化响应参数
 * 可自定义响应视图
 */
public class PageUtils {

    public static  <T> IPage<T> newPage(Pageable p){
        return new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(p.getPage(), p.getSize(), p.getTotal());
    }

    public static <T> Page<T> newPage(IPage<T> page){
        return new Page<>(page.getTotal(), page.getRecords());
    }
}
