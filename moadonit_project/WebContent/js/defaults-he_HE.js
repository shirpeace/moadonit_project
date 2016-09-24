/*
 * Translated default messages for bootstrap-select.
 * Locale: EN (English)
 * Region: US (United States)
 */
(function ($) {
  $.fn.selectpicker.defaults = {
    noneSelectedText: 'לא נבחר פריט',
    noneResultsText: 'אין תוצאות עבור {0}',
    countSelectedText: function (numSelected, numTotal) {
      return (numSelected == 1) ? "{0} פריט נבחר" : "{0} פריטים נבחרו";
    },
    maxOptionsText: function (numAll, numGroup) {
      return [
        (numAll == 1) ? 'Limit reached ({n} item max)' : 'Limit reached ({n} items max)',
        (numGroup == 1) ? 'Group limit reached ({n} item max)' : 'Group limit reached ({n} items max)'
      ];
    },
    selectAllText: 'בחר הכל',
    deselectAllText: 'בטל הכל',
    multipleSeparator: ', '
  };
})(jQuery);
