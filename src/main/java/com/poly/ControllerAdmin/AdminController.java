package com.poly.ControllerAdmin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.OrdersDao;
import com.poly.entity.Orders;
import com.poly.services.StatisticalService;




@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	OrdersDao ordersDao;
	// Phương thức xử lý yêu cầu truy cập vào các đường dẫn rỗng hoặc "/index"
	@GetMapping({"", "/", "/index"})
	public String index(Model model,
	        @RequestParam(value = "dateDay", required = false) String dateDayParam,
	        @RequestParam(value = "dateYear", required = false) String dateYearParam,
	        @RequestParam(value = "dateMonth", required = false) String dateMonthParam) throws ParseException {

	    // Xử lý ngày hiện tại hoặc ngày được chọn
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date dateDay = new Date();
	    if (dateDayParam != null)
	        dateDay = dateFormat.parse(dateDayParam);

	    Date dateToday = getDayMonthYear(dateDay);

	    // Thống kê đơn hàng trong ngày
	    List<Orders> ordersDays = ordersDao.findByCreateDate(dateToday);
	    float countTDay = 0;
	    for (Orders ordersDay : ordersDays) {
	        countTDay += ordersDay.getPrice();
	    }

	    // Thêm thông tin vào model
	    model.addAttribute("dateDay", dateToday);
	    model.addAttribute("countDtDay", ordersDays.size());
	    model.addAttribute("countTDay", countTDay);

	    // Xử lý tháng
	    Date dateMonth = new Date();
	    if (dateMonthParam != null) {
	        dateMonthParam += "-01";
	        dateMonth = dateFormat.parse(dateMonthParam);
	    }
	    Date dateToMonth = getDayMonthYear(dateMonth);

	    float countTMonth = 0;
	    List<Orders> ordersMonths = ordersDao.findByCreateDateMonth(dateToMonth);
	    for (Orders ordersMonth : ordersMonths) {
	        countTMonth += ordersMonth.getPrice();
	    }

	    model.addAttribute("dateMonth", dateMonthParam);
	    model.addAttribute("countDtMonth", ordersMonths.size());
	    model.addAttribute("countTMonth", countTMonth);

	    // Xử lý năm
	    Date dateYear = new Date();
	    if (dateYearParam != null) {
	        dateYearParam += "-01-01";
	        dateYear = dateFormat.parse(dateYearParam);
	    }
	    Date dateToYear = getDayMonthYear(dateYear);

	    float countTYear = 0;
	    List<Orders> ordersYears = ordersDao.findByCreateDateYear(dateToYear);
	    for (Orders ordersYear : ordersYears) {
	        countTYear += ordersYear.getPrice();
	    }

	    model.addAttribute("dateYear", dateYearParam);
	    model.addAttribute("countDtYear", ordersYears.size());
	    model.addAttribute("countTYear", countTYear);

	    return "views/admin/index";
	}

	// Hàm chuyển đổi ngày về định dạng chỉ có ngày, tháng, năm
	public Date getDayMonthYear(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    if (date != null)
	        calendar.setTime(date);
	    else
	        calendar.setTime(new Date());

	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	}

	 
}
