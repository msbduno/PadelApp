<?php


  if (isset($_POST['action'])) {
    $action = $_POST['action'];
    
        $value = $_POST['value'] ?? null;
        if ($value === 'next' || $value === 'previous') {
          $_SESSION['current_time']->modify(($value === 'next') ? '+1 week' : '-1 week');
          echo 'success changeWeek';
        }
       
  }
?>