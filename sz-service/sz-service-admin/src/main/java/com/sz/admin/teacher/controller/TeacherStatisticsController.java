package com.sz.admin.teacher.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.constant.GlobalConstant;

import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.admin.teacher.service.TeacherStatisticsService;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsCreateDTO;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsUpdateDTO;
import com.sz.admin.teacher.pojo.dto.TeacherStatisticsListDTO;
import com.sz.admin.teacher.pojo.vo.TeacherStatisticsVO;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 教师统计总览表 Controller
 * </p>
 *
 * @author sz-admin
 * @since 2024-06-19
 */
@Tag(name =  "教师统计总览表")
@RestController
@RequestMapping("teacher-statistics")
@RequiredArgsConstructor
public class TeacherStatisticsController  {

    private final TeacherStatisticsService teacherStatisticsService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "teacher.statistics.create", orRole = GlobalConstant.SUPER_ROLE)
    @PostMapping
    public ApiResult create(@RequestBody TeacherStatisticsCreateDTO dto) {
        teacherStatisticsService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "teacher.statistics.update", orRole = GlobalConstant.SUPER_ROLE)
    @PutMapping
    public ApiResult update(@RequestBody TeacherStatisticsUpdateDTO dto) {
        teacherStatisticsService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "teacher.statistics.remove", orRole = GlobalConstant.SUPER_ROLE)
    @DeleteMapping
    public ApiResult remove(@RequestBody SelectIdsDTO dto) {
        teacherStatisticsService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "teacher.statistics.query_table", orRole = GlobalConstant.SUPER_ROLE)
    @GetMapping
    public ApiResult<PageResult<TeacherStatisticsVO>> list(TeacherStatisticsListDTO dto) {
        return ApiPageResult.success(teacherStatisticsService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "teacher.statistics.query_table", orRole = GlobalConstant.SUPER_ROLE)
    @GetMapping("/{id}")
    public ApiResult<TeacherStatisticsVO> detail(@PathVariable Object id) {
        return ApiResult.success(teacherStatisticsService.detail(id));
    }

    @Operation(summary = "导入")
    @Parameters({
      @Parameter(name = "file", description = "上传文件", schema = @Schema(type = "string", format = "binary"), required = true),
    })
    @SaCheckPermission(value = "teacher.statistics.import", orRole = GlobalConstant.SUPER_ROLE)
    @PostMapping("/import")
    public void importExcel(MultipartFile file) {
        teacherStatisticsService.importExcel(file);
    }

    @Operation(summary = "导出")
    @SaCheckPermission(value = "teacher.statistics.export", orRole = GlobalConstant.SUPER_ROLE)
    @PostMapping("/export")
    public void exportExcel(TeacherStatisticsListDTO dto, HttpServletResponse response) {
        teacherStatisticsService.exportExcel(dto, response);
    }

}
