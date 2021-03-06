package com.fsd.file.controller;


import com.fsd.file.entity.StockPriceEntity;
import com.fsd.file.service.StockPriceService;
import com.fsd.file.service.impl.StockPriceImportRowReaderImpl;
import com.fsd.file.utils.CommonResult;
import com.fsd.file.utils.ResponseCode;
import com.fsd.file.utils.excel2007.Excel2007ParseResponse;
import com.fsd.file.utils.excel2007.Excel2007Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/admin/import")
public class SyncImportController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StockPriceService stockPriceService;

	/**
	 * import data
	 * @param file
	 * @return
	 */
	@PostMapping("/data")
	public CommonResult query(@RequestParam("file") MultipartFile file) {
		List<StockPriceEntity> validDatas = new ArrayList();
		try {
			long time = System.currentTimeMillis();
			Excel2007ParseResponse parseResponse =
					new Excel2007Reader<StockPriceEntity>().process(file.getInputStream(),
							new StockPriceImportRowReaderImpl(),
							1, false);
			if (!StringUtils.isEmpty(parseResponse.getMessage())) {
				return CommonResult.build(ResponseCode.ERROR_SERVICE_UNAVAILABLE, "please check excel");
			} else {
				validDatas.addAll(parseResponse.getDatas());
				logger.info("总记录数：{},解析耗时（秒）：{}", validDatas.size(),
						(System.currentTimeMillis() - time) / 1000);
				// 进行入库
				return stockPriceService.insert(validDatas);
			}
		} catch (IOException | InvalidFormatException e) {
			logger.error("");
			return CommonResult
					.build(ResponseCode.ERROR_SERVICE_UNAVAILABLE, "ERROR_SERVICE_UNAVAILABLE");
		}
	}

}
