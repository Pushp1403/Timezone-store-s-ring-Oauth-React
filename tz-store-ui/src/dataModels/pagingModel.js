class PagingModel {
  constructor(nextPage, pageSize) {
    this.nextPage = nextPage ? nextPage : 0;
    this.pageSize = pageSize ? pageSize : 20;
  }
}

export default PagingModel;
