package com.ecommerce.app.order;

import com.ecommerce.app.appuser.AppUserService;
import com.ecommerce.app.appuser.address.UserAddressService;
import com.ecommerce.app.exceptionhandler.APIException;
import com.ecommerce.app.exceptionhandler.ResourceNotFoundException;
import com.ecommerce.app.order.Order.OrderStatus;
import com.ecommerce.app.orderitem.*;
import com.ecommerce.app.product.ProductDTO;
import com.ecommerce.app.product.ProductService;
import com.ecommerce.app.util.PageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepo orderRepo;
    private final OrderStatusRepo orderStatusRepo;
    private final ProductService productService;
    private final UserAddressService userAddressService;
    private final AppUserService appUserService;

    public OrderServiceImpl(OrderRepo orderRepo, OrderStatusRepo orderStatusRepo, ProductService productService, UserAddressService userAddressService, AppUserService appUserService)
    {
        this.orderRepo = orderRepo;
        this.orderStatusRepo = orderStatusRepo;
        this.productService = productService;
        this.userAddressService = userAddressService;
        this.appUserService = appUserService;
    }

    public OrderViewDTO placeOrder(OrderRequestDTO orderRequestDTO, long userId)
    {
        Order order = new Order();
        order.setBuyerId(userId);
        order.setOrderDate(LocalDateTime.now());
//        order.setStatus(OrderStatus.CREATED);

//        List<OrderItem> orderItems = buildOrderItems(orderRequestDTO.orderItemRequestDTOS, order);
//        order.setOrderItems(orderItems);

//        double totalOrderPrice = orderItems.stream().mapToDouble(oi -> (oi.getProductPrice()*oi.getQuantity())).sum();
//        order.setTotalAmount(totalOrderPrice);

        ProductDTO productDTO = productService.getProductObjForProductId(orderRequestDTO.productId);

        order.setProductId(productDTO.id);
        order.setProductName(productDTO.productName);
        order.setQuantity(orderRequestDTO.productQuantity);
        order.setProductPrice(productDTO.productPrice);
        order.setTotalAmount(productDTO.productPrice*orderRequestDTO.productQuantity);
        order.setSellerId(productDTO.sellerId);
        order.setAddressId(orderRequestDTO.addressId);
        order.setCurStatus(OrderStatus.ORDER_PLACED);
        //order.setPayment(); todo

        order.getOrderStatusHistories().add(
                new OrderStatusHistory(
                    OrderStatus.ORDER_PLACED,
                    LocalDateTime.now(),
                    order)
        );
//        OrderStatusHistory orderStatus = new OrderStatusHistory();
//        orderStatus.setOrder(order);
//        orderStatus.setOrderStatus(OrderStatus.ORDER_PLACED);
//        orderStatus.setStatusDateTime(LocalDateTime.now());

//        order.setOrderStatusHistories(orderStatus);

        Order savedOrder = saveOrderObj(order);
        return convertToDTO(savedOrder);
    }

//    public List<OrderItem> buildOrderItems(List<OrderItemRequestDTO> orderItemRequestDTOS, Order order)
//    {
//        return orderItemRequestDTOS.stream().map(orderItemRequestDTO -> {
//            ProductDTO productDTO = productService.getProductObjForProductId(orderItemRequestDTO.productId);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProductId(productDTO.id);
//            orderItem.setQuantity(orderItemRequestDTO.productQuantity);
//            orderItem.setProductPrice(productDTO.productPrice);
//            orderItem.setOrder(order);
//            orderItem.setSellerId(productDTO.sellerId);
//
//            return orderItem;
//        }).collect(Collectors.toList());
//    }

    private OrderViewDTO convertToDTO(Order order)
    {
        OrderViewDTO orderDTO = new OrderViewDTO();

        orderDTO.orderId = order.getId();
        orderDTO.userName = appUserService.getUserName(order.getBuyerId());
        orderDTO.totalAmount = order.getTotalAmount();
        orderDTO.orderDate = order.getOrderDate().toLocalDate().toString();
//        orderDTO.orderStatus = order.getStatus().name();
        orderDTO.address = userAddressService.fetchSpecificAddressForUser(order.getBuyerId(), order.getAddressId());

        orderDTO.productId = order.getProductId();
        orderDTO.productName = order.getProductName();
        orderDTO.productPrice = order.getProductPrice();
        orderDTO.quantity = order.getQuantity();
        orderDTO.sellerName = appUserService.getUserName(order.getSellerId());

        orderDTO.currentOrderStatus = order.getCurStatus().name();

        System.out.println("name -> " + order.getCurStatus().name());
        System.out.println("label -> " + order.getCurStatus().toString());

        orderDTO.orderStatusHistoryDTOS = order.getOrderStatusHistories()
                .stream().map(os -> {
                    OrderStatusHistoryDTO dto = new OrderStatusHistoryDTO();
                    dto.id = os.getId();
                    dto.orderId = os.getOrder().getId();
                    dto.orderStatus = os.getOrderStatus().name();

//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    dto.statusTime = os.getStatusDateTime().format(formatter);
                    dto.statusTime = os.getStatusDateTime();

                    return dto;
                }).collect(Collectors.toList());

//        orderDTO.items = order.getItems().stream().map(i -> {
//            OrderItemDTO orderItemDTO = new OrderItemDTO();
//
//            orderItemDTO.orderItemId = i.getId();
//            orderItemDTO.productName = productService.getProductObjForProductId(i.getProductId()).productName;
//            orderItemDTO.productPrice = i.getProductPrice();
//            orderItemDTO.quantity = i.getQuantity();
//            orderItemDTO.sellerName = appUserService.getUserName(i.getSellerId());
//
//            return orderItemDTO;
//        }).collect(Collectors.toList());

        return orderDTO;
    }

    //orders for user (user's order history)
    public PageDetails<OrderViewDTO> fetchOrdersForUser(long userId, int pageNum, int pageSize, String sortBy, String sortOrder)
    {
        System.out.println("fetch order for user !!!!");
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);
        Page<Order> orderPage = orderRepo.findAllByBuyerId(userId, pageDetails);

        System.out.println("order page -> " + orderPage.getContent());
        return getOrderDTOPageDetails(orderPage);
    }

    private PageDetails<OrderViewDTO> getOrderDTOPageDetails(Page<Order> orderPage)
    {
        List<Order> orders = orderPage.getContent();

        if(orders.isEmpty())
        {
            throw new APIException("No orders available!!");
        }

        List<OrderViewDTO> orderDTOS = orders.stream().map(this::convertToDTO).toList();
        System.out.println("orderDTOS -> " + orderDTOS);

        return new PageDetails<>(
                orderDTOS,
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast()
        );
    }

    public OrderViewDTO buildOrderDetail(long orderId)
    {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        return convertToDTO(order);
    }


    public OrderViewDTO updateOrderStatus(OrderStatusUpdateDTO orderStatusUpdateDTO) {
        Order order = getOrderObj(orderStatusUpdateDTO.orderId);

        OrderStatus newOrderStatus = OrderStatus.from(orderStatusUpdateDTO.orderStatus);

        for(OrderStatusHistory os : order.getOrderStatusHistories())
        {
            if(os.getOrderStatus().equals(newOrderStatus))
            {
                throw new APIException("Status - " + newOrderStatus + " already exists!!");
            }
        }

        order.setCurStatus(newOrderStatus);

        order.getOrderStatusHistories().add(
                new OrderStatusHistory(
                        newOrderStatus,
                        LocalDateTime.now(),
                        order)
        );

        Order updatedOrder = orderRepo.save(order);

        return convertToDTO(updatedOrder);
    }

    public Order getOrderObj(long orderId) {
         return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "order", orderId));
    }

    public Order saveOrderObj(Order order)
    {
        return orderRepo.save(order);
    }

    public OrderViewDTO getOrderData(long orderId)
    {
        return convertToDTO(orderRepo.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "order id", orderId))
        );
    }

    public OrderViewDTO deleteOrder(long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order", "orderId", orderId));

        orderRepo.delete(order);

        return convertToDTO(order);
    }

    public PageDetails<OrderViewDTO> listOrdersForMonth(int year, int month, int pageNum, int pageSize, String sortBy, String sortOrder)
    {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);

        OffsetDateTime start = OffsetDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime end = start.plusMonths(1);

        Page<Order> orderPage = orderRepo.findByOrderDateBetween(start, end, pageDetails);

        return getOrderDTOPageDetails(orderPage);
    }

//    public PageDetails<OrderItemDTO> fetchOrdersForSeller(long sellerId, int pageNum, int pageSize, String sortBy, String sortOrder)
//    {
//        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageDetails = PageRequest.of(pageNum, pageSize, sortByAndOrder);
//
//        Page<OrderItem> orderItems = orderItemRepo.findAllBySellerId(sellerId, pageDetails);
///
//        List<OrderItemDTO> orderItemDTOS = orderItems.getContent().stream().map(oi -> {
//            OrderItemDTO dto = new OrderItemDTO();
//
//            dto.productName = productService.getProductObjForProductId(oi.getProductId()).productName;
//            dto.orderItemId = oi.getId();
//            dto.sellerName = appUserService.getUserName(oi.getSellerId());
//            dto.quantity = oi.getQuantity();
//            dto.productPrice = oi.getProductPrice();
//
//            return dto;
//        }).toList();
//
//        return new PageDetails<>(
//                orderItemDTOS,
//                orderItems.getNumber(),
//                orderItems.getSize(),
//                orderItems.getTotalElements(),
//                orderItems.getTotalPages(),
//                orderItems.isLast()
//        );
//    }

}
